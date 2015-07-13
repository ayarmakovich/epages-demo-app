package com.epages.demo.app.installation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository
public class AppInstallationRepository {

    private Map<String, AppInstallation> appInstallations = new ConcurrentHashMap<>();

    public AppInstallation findByApiUrl(String shop) {
        return appInstallations.get(shop);
    }

    public List<AppInstallation> findAll() {
        return new ArrayList<>(appInstallations.values());
    }

    public void save(AppInstallation appInstallation) {
        appInstallations.put(appInstallation.getApiUrl(), appInstallation);
    }

    public void deleteByApiUrl(String shop) {
        appInstallations.remove(shop);
    }

    public void deleteAll() {
        appInstallations.clear();
    }
}
