package com.demo.ctw.entity;

import java.util.ArrayList;

public class AddressObject {
    private String areaId;
    private String areaName;
    private ArrayList<City> cities;

    public String getAreaId() {
        return areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public class City extends Area {
        private ArrayList<Area> counties;

        public City(String areaId, String areaName) {
            super(areaId, areaName);
        }

        public ArrayList<Area> getCounties() {
            return counties;
        }
    }

    public static class Area {
        private String areaId;
        private String areaName;

        public Area(String areaId, String areaName) {
            this.areaId = areaId;
            this.areaName = areaName;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }
    }
}
