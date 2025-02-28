package settings.controller;

import burp.api.montoya.persistence.Preferences;
import settings.debug.DebugSettings;
import settings.defaultsavelocation.DefaultSaveLocationSettings;
import settings.repository.RepositorySettings;
import settings.repository.filesystem.FileSystemRepositorySettings;
import settings.repository.github.GitHubSettings;

import static data.ItemMetadata.BCHECK;

public class SettingsController {
    private final DefaultSaveLocationSettings defaultSaveLocationSettings;
    private final GitHubSettings gitHubSettings;
    private final DebugSettings debugSettings;
    private final FileSystemRepositorySettings fileSystemRepositorySettings;
    private final RepositorySettings repositorySettings;

    public SettingsController(Preferences preferences) {
        this.defaultSaveLocationSettings = new DefaultSaveLocationSettings(preferences, BCHECK);
        this.repositorySettings = new RepositorySettings(preferences, BCHECK);
        this.gitHubSettings = new GitHubSettings(preferences, BCHECK);
        this.fileSystemRepositorySettings = new FileSystemRepositorySettings(preferences, BCHECK);
        this.debugSettings = new DebugSettings(preferences);
    }

    public DefaultSaveLocationSettings defaultSaveLocationSettings() {
        return defaultSaveLocationSettings;
    }

    public RepositorySettings repositorySettings() {
        return repositorySettings;
    }

    public GitHubSettings gitHubSettings() {
        return gitHubSettings;
    }

    public FileSystemRepositorySettings fileSystemRepositorySettings() {
        return fileSystemRepositorySettings;
    }

    public DebugSettings debugSettings() {
        return debugSettings;
    }
}
