package ir.jahanmir.aspa.events;

import java.util.List;

import ir.jahanmir.aspa.gson.ChargeOnlineGroup;


public class EventOnGetChargeOnlineForLoadGroups {
    List<ChargeOnlineGroup> chargeOnlineGroups;
    public EventOnGetChargeOnlineForLoadGroups(List<ChargeOnlineGroup> chargeOnlineGroups) {
        this.chargeOnlineGroups = chargeOnlineGroups;
    }
    public List<ChargeOnlineGroup> getChargeOnlineGroups() {
        return chargeOnlineGroups;
    }
}
