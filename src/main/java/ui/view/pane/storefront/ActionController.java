package ui.view.pane.storefront;

import bcheck.BCheck;
import ui.controller.StoreController;
import ui.model.StorefrontModel;

import java.nio.file.Path;
import java.util.concurrent.Executor;

import static ui.view.component.filechooser.ChooseMode.FILES_ONLY;
import static ui.view.pane.storefront.ActionCallbacks.INERT_CALLBACKS;

class ActionController {
    private final StorefrontModel model;
    private final StoreController storeController;
    private final SaveLocation saveLocation;
    private final Executor executor;

    ActionController(StorefrontModel model, StoreController storeController, SaveLocation saveLocation, Executor executor) {
        this.model = model;
        this.storeController = storeController;
        this.saveLocation = saveLocation;
        this.executor = executor;
    }

    void copySelectedBCheck() {
        BCheck selectedBCheck = model.getSelectedBCheck();
        storeController.copyBCheck(selectedBCheck);
        model.setStatus("Copied BCheck " + selectedBCheck.name() + " to clipboard");
    }

    void saveSelectedBCheck() {
        saveSelectedBCheck(INERT_CALLBACKS);
    }

    void saveSelectedBCheck(ActionCallbacks actionCallbacks) {
        BCheck selectedBCheck = model.getSelectedBCheck();

        saveLocation.find(FILES_ONLY, selectedBCheck.filename())
                .ifPresent(path -> {
                    actionCallbacks.actionBegun();
                    model.setStatus("");

                    executor.execute(() -> {
                        Path savePath = path.toFile().isDirectory() ? path.resolve(selectedBCheck.filename()) : path;

                        storeController.saveBCheck(selectedBCheck, savePath);
                        model.setStatus("Saved BCheck to " + savePath);
                        actionCallbacks.actionComplete();
                    });
                });
    }

    void saveAllVisibleBChecks(ActionCallbacks actionCallbacks) {
        saveLocation.find().ifPresent(path -> {
            actionCallbacks.actionBegun();
            model.setStatus("");

            executor.execute(() -> {
                model.getFilteredBChecks().forEach(bCheck -> storeController.saveBCheck(bCheck, path.resolve(bCheck.filename())));
                model.setStatus("Saved all BChecks to " + path);
                actionCallbacks.actionComplete();
            });
        });
    }
}