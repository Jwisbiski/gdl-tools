package se.cambio.openehr.util;

import se.cambio.cm.model.facade.administration.delegate.ClinicalModelsService;
import se.cambio.cm.model.util.CMElement;
import se.cambio.openehr.util.exceptions.InternalErrorException;

import java.util.Calendar;
import java.util.Date;

public class CachedCMManager {

    private static final long MAX_CHECK_WAITING_TIME_IN_MILLIS = 30000; //30 seg
    private Date lastCheckForUpdates;
    private Date mostRecentLocalUpdate;
    private Class<? extends CMElement> cmElementClass;
    private ClinicalModelsService clinicalModelsService;


    public CachedCMManager(
            Class<? extends CMElement> cmElementClass,
            ClinicalModelsService clinicalModelsService) {
        this.cmElementClass = cmElementClass;
        this.clinicalModelsService = clinicalModelsService;
        Date currentTime = Calendar.getInstance().getTime();
        mostRecentLocalUpdate = currentTime;
        lastCheckForUpdates = currentTime;
    }

    public boolean isDataChanged() throws InternalErrorException {
        boolean changesDetected = false;
        if (isWaitingTimeForNextCheckReached()) {
            changesDetected = checkIfDataChanged();
        }
        return changesDetected;
    }

    private boolean checkIfDataChanged() throws InternalErrorException {
        boolean changesDetected = false;
        Date lastUpdateDateOnServer = this.clinicalModelsService.getLastUpdate(cmElementClass);
        if (lastUpdateDateOnServer != null) {
            if (lastUpdateDateOnServer.getTime() > mostRecentLocalUpdate.getTime()) {
                changesDetected = true;
                mostRecentLocalUpdate = lastUpdateDateOnServer;
            }
        }
        return changesDetected;
    }

    private boolean isWaitingTimeForNextCheckReached() {
        boolean waitingTimeForNextCheckReached = false;
        long timeSinceLastCheck = System.currentTimeMillis() - lastCheckForUpdates.getTime();
        if (timeSinceLastCheck >= MAX_CHECK_WAITING_TIME_IN_MILLIS) {
            waitingTimeForNextCheckReached = true;
            lastCheckForUpdates = Calendar.getInstance().getTime();
        }
        return waitingTimeForNextCheckReached;
    }

    public void renewMostRecentLocalUpdate(Date lastUpdate) {
        if (lastUpdate != null && lastUpdate.after(mostRecentLocalUpdate)) {
            mostRecentLocalUpdate = lastUpdate;
        }
    }
}
