package de.novatec.vccclient.controller;

import de.novatec.vccclient.datapool.entity.DebugDataPool;
import de.novatec.vccclient.kubernetes.KubernetesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DebugViewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebugViewController.class);

    @Autowired
    private KubernetesManager kubernetesManager;

    @GetMapping("/client/debug")
    public String debug(Model model) {
        List<DebugDataPool> debugDataPools = kubernetesManager.getDebugDataPools();
        model.addAttribute("datapools", debugDataPools);
        return "debugview";
    }

    @GetMapping("/client/debug/pod/kill/{podId}")
    public String killPod(@PathVariable String podId) {
        kubernetesManager.killPodById(podId);
        return "redirect:/client/debug";
    }

    @GetMapping("/client/debug/cpuload/start/{podId}")
    public String startCpuLoad(@PathVariable String podId) {
        kubernetesManager.overloadPodById(podId);
        return "redirect:/client/debug";
    }

    @GetMapping("/client/debug/cpuload/stop/{podId}")
    public String stopCpuLoad(@PathVariable String podId) {
        kubernetesManager.stopOverloadPodById(podId);
        return "redirect:/client/debug";
    }
}
