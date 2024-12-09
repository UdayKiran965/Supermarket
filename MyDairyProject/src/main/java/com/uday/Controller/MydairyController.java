package com.uday.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uday.Service.MydairyService;
import com.uday.binding.Mydairy;

@Controller
@RequestMapping("/diary")
public class MydairyController {
    @Autowired
    private MydairyService service;

    // Change the mapping of viewHomePage to a unique path
    @GetMapping("/home") // Changed from "/" to "/home"
    public String viewHomePage(Model model) {
        model.addAttribute("entries", service.getAllEntries());
        return "home"; // Return the view name for the home page
    }

    @GetMapping("/index")
    public String index() {
        return "index"; // Return the view name for the index page
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("entry", new Mydairy());
        return "add-entry"; // Return the view name for the add entry form
    }

    @PostMapping("/save")
    public String saveEntry(@ModelAttribute("entry") Mydairy entry) {
        service.saveEntry(entry);
        return "redirect:/diary/home"; // Redirect to the home page after saving
    }

    @GetMapping("/delete/{id}")
    public String deleteEntry(@PathVariable("id") Long id) {
        service.deleteEntry(id); // Call the service method to delete the entry
        return "redirect:/diary/home"; // Redirect to the home page after deletion
    }

    @GetMapping("/view/{id}")
    public String viewEntry(@PathVariable("id") Long id, Model model) {
        Mydairy entry = service.getEntry(id);
        model.addAttribute("entry", entry);
        return "view-entry"; // Return the view name to display a single entry
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Mydairy entry = service.getEntry(id);
        model.addAttribute("entry", entry);
        return "edit-entry"; // Return the view name for editing the entry
    }
}