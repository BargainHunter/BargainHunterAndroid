package com.bargainhunter.bargainhunterandroid.gps;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vasovourka on 11/9/14.
 */
public class MockLocationsController {
    MockLocations mock1;
    List<MockLocations> loc = new ArrayList<>();

    public List<MockLocations> fetchLocations() {
        mock1 = new MockLocations();
        mock1.setFullAdress("serres");
        mock1.setLatitude(41.083153);
        mock1.setLongitude(23.556173);
        loc.add(mock1);
        System.out.print(loc);
        return loc;
    }
}
