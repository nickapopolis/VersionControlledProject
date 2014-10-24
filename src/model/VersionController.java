package model;

import java.io.File;
import java.io.IOException;

import main.FileUtils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import view.CredentialsPromptDialog;
import view.UpdatePromptDialog;

public class VersionController {
	
	ProjectSettings projectSettings;
	UsernamePasswordCredentialsProvider credentials;
	FileRepository repository;
	Git git;
	
	public VersionController() {
				
		try {
			//initialize project settings
			projectSettings = new ProjectSettings()
			{
				@Override
				public void onSettingsInitialized() {
					try {
						cloneProject(get("remoteURI"), get("installPath"));
						saveSettings();
					} catch (Exception e) {
						System.err.println("Could not clone project");
						e.printStackTrace();
					} 	
				}
			};
			//check for updates
			if(hasUpdates()){
				System.out.println("Updates found.");
				if(new UpdatePromptDialog().willUpdate()){
					getAndApplyUpdates();
				}
			}else{
				System.out.println("Up to date.");
			}
			//run project
			runProject();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void cloneProject(String remotePath, String localPath) throws Exception {
		
		Git.cloneRepository().setURI(remotePath).setDirectory(new File(localPath )).setCredentialsProvider(getCredentials()).call();

	}
	private boolean hasUpdates() throws Exception{
		
		FileRepository repository = getRepository();
		Git git = getGit();
		
		boolean hasUpdates = false;
		git.fetch().setCredentialsProvider(getCredentials()).call();
		
		RevWalk walk = new RevWalk(repository);
		walk.markStart(walk.parseCommit(repository.resolve("refs/remotes/origin/master")));
		walk.markUninteresting(walk.parseCommit(repository.resolve("refs/heads/master")));
		
		for (RevCommit commit : walk) {
			hasUpdates = true;
			break;
		}

		walk.dispose();
		
		return hasUpdates;
	}
	private void getAndApplyUpdates() throws IOException, WrongRepositoryStateException, InvalidConfigurationException, DetachedHeadException, InvalidRemoteException, CanceledException, RefNotFoundException, NoHeadException, TransportException, GitAPIException{
		
		Git git = getGit();
		git.pull().call();
	}
	private void runProject() throws IOException{
		Runtime rt = Runtime.getRuntime();
		String[] commands = FileUtils.readFileIntoString(projectSettings.get("installPath") + "\\.exec", false).split("\n");
		rt.exec(commands, null, new File(projectSettings.get("installPath")));
	}
	private UsernamePasswordCredentialsProvider getCredentials() throws Exception{
		if(credentials == null){
			credentials = new CredentialsPromptDialog().getCredentials();
		}
		return credentials;
	}
	private FileRepository getRepository() throws IOException{
		if(repository == null){
			repository = new FileRepository(projectSettings.get("installPath") + "\\.git");
		}
		return repository;
	}
	private Git getGit() throws IOException{
		if(git == null){
			git = new Git(getRepository());
		}
		return git;
	}

}
