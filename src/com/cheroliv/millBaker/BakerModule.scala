package com.cheroliv.millBaker

import de.tobiasroeser.mill.jbake.JBakeModule
import mill.T
import mill.api.PathRef
import mill.api.Result
import mill.define.Command
import mill.define.Task
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.transport.URIish
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider

trait BakerModule extends JBakeModule {

  def websiteConfig: SiteConfiguration

  def websiteSources: T[PathRef] = jbake

  def publishSite(): Command[Unit] = Task.Command {
    val config = websiteConfig

    val passwordOrConfig = config.pushConfiguration.repo.credentials.password
      .orElse(sys.env.get("PUSH_TOKEN"))
      .getOrElse(throw new IllegalArgumentException("No password found"))

    os.copy.over(jbake().path, T.dest)
    
    config.cname.foreach(cname => os.write(T.dest / "CNAME", cname))

    val git =
      Git
        .init
        .setDirectory(T.dest.toIO)
        .setInitialBranch(config.pushConfiguration.branch)
        .call()

    val repository = git.getRepository
    
    if (repository.isBare) Result.Failure("Repository is bare")
    else {
      T.log.info("Repository is not bare")

      if (!repository.getDirectory.isDirectory) Result.Failure("Repository file must be a directory")
      else {
        T.log.info("Repository file is a directory")
        // add remote repo:
        git
          .remoteAdd
          .setName(RepositoryConfiguration.Origin)
          .setUri(new URIish(config.pushConfiguration.repo.repository))
          .call()
        //4) ajouter les fichiers du dossier cvs à l'index
        git.add.addFilepattern(".").call()
        //5) commit
        git.commit.setMessage(config.pushConfiguration.message).call()

        git
          .push
          .setCredentialsProvider(
            new UsernamePasswordCredentialsProvider(
              config.pushConfiguration.repo.credentials.username,
              passwordOrConfig
            )
          )
          .setRemote(RepositoryConfiguration.Origin)
          .setForce(true)
          .call()
        
        Result.Success(())
      }
    }
  }
}