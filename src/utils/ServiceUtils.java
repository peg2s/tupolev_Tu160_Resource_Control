package utils;

import data.Aircraft;
import data.Component;
import data.SavedData;
import data.TextConstants;
import data.enums.ComponentType;
import javafx.scene.control.Alert;

import java.util.List;

public class ServiceUtils {
    public static void showWarning(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(TextConstants.ATTENTION);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Проверка, чтобы на один борт нельзя было добавить больше заданного производителем агрегатов,
     * а также проверка на несовместимость уже установленных агрегатов с создаваемым.
     * Кроме этого проверяется номер, он должен быть уникальным.
     *
     * @param aircraft
     * @param component
     * @return разрешено ли добавление
     */
    public static boolean checkComponentsOnAircraft(Aircraft aircraft, Component component) {
        if (SavedData.components.stream().anyMatch(c -> c.getNumber().equals(component.getNumber()))) {
            showWarning(TextConstants.COMPONENT_ALREADY_EXISTS);
            return false;
        }
        List<String> componentsOnAircraft = aircraft.getComponents();
        int mpuCount = (int) componentsOnAircraft.stream().filter(c -> c.contains(ComponentType.MPU.getName())).count();
        int mkuCount = (int) componentsOnAircraft.stream().filter(c -> c.contains(ComponentType.MKU.getName())).count();
        int l029Count = (int) componentsOnAircraft.stream().filter(c -> c.contains(ComponentType.L029.getName())).count();
        ComponentType type = component.getType();
        if (componentsOnAircraft.size() == 0) {
            return true;
        } else if ((type == ComponentType.MKU && mpuCount != 0)
                || (type == ComponentType.MPU && mkuCount != 0)) {
            showWarning(TextConstants.SIMULTANEOUSLY_INSTALLATION_MKU_MPU);
            return false;
        } else if ((type == ComponentType.MKU && mkuCount == 2)
                || (type == ComponentType.MPU && mpuCount == 2)
                || (type == ComponentType.L029 && l029Count == 3)) {
            showWarning(TextConstants.MAX_COUNT_OF_COMPONENTS + type.getName());
            return false;
        }
        return true;
    }
}