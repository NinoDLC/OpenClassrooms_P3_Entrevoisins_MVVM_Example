package fr.delcey.entrevoisins.config;

import fr.delcey.entrevoisins.BuildConfig;

// This class is very useful for unit testing
public class BuildConfigResolver {

    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
