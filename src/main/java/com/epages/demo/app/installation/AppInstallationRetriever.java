package com.epages.demo.app.installation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AppInstallationRetriever {

    @Autowired
    private AppInstallationRepository appInstallationRepository;

    @ModelAttribute("appInstallations")
    public List<AppInstallation> retrieveAppInstallations() {
        return appInstallationRepository.findAll();
    }
}
