package example.com.model;

import com.google.gson.annotations.SerializedName;

public class Conflict {
    @SerializedName("country_id")
    String countryId;
    String region;
    @SerializedName("date_start")
    String dateStart;
    @SerializedName("date_end")
    String dateEnd;
    @SerializedName("deaths_a")
    String deathsA;
    @SerializedName("deaths_b")
    String deathsB;
    @SerializedName("deaths_civilians")
    String deathsCivilians;
    @SerializedName("deaths_unknown")
    String deathsUnknown;
    String id;
    String year;
    @SerializedName("conflict_new_id")
    String conflictNewId;
    @SerializedName("conflict_name")
    String conflictName;
    @SerializedName("dyad_new_id")
    String dyadNewId;
    @SerializedName("dyad_name")
    String dyadName;
    @SerializedName("side_a")
    String sideA;
    @SerializedName("side_b")
    String sideB;
    @SerializedName("source_article")
    String sourceArticle;
    @SerializedName("source_office")
    String sourceOffice;
    @SerializedName("source_date")
    String sourceDate;
    @SerializedName("source_headline")
    String sourceHeadline;
    @SerializedName("source_original")
    String sourceOriginal;
    @SerializedName("where_coordinates")
    String whereCoordinates;
    String latitude;
    String longitude;
    String country;
    boolean isLoading;

    public Conflict() {}

    public Conflict(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public Conflict(String id, String conflict_name, String latitude, String longitude, String country) {
        this.id = id;
        this.conflictName = conflict_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDeathsA() {
        return deathsA;
    }

    public void setDeathsA(String deathsA) {
        this.deathsA = deathsA;
    }

    public String getDeathsB() {
        return deathsB;
    }

    public void setDeathsB(String deathsB) {
        this.deathsB = deathsB;
    }

    public String getDeathsCivilians() {
        return deathsCivilians;
    }

    public void setDeathsCivilians(String deathsCivilians) {
        this.deathsCivilians = deathsCivilians;
    }

    public String getDeathsUnknown() {
        return deathsUnknown;
    }

    public void setDeathsUnknown(String deathsUnknown) {
        this.deathsUnknown = deathsUnknown;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getConflictNewId() {
        return conflictNewId;
    }

    public void setConflictNewId(String conflictNewId) {
        this.conflictNewId = conflictNewId;
    }

    public String getConflictName() {
        return conflictName;
    }

    public void setConflictName(String conflictName) {
        this.conflictName = conflictName;
    }

    public String getDyadNewId() {
        return dyadNewId;
    }

    public void setDyadNewId(String dyadNewId) {
        this.dyadNewId = dyadNewId;
    }

    public String getDyadName() {
        return dyadName;
    }

    public void setDyadName(String dyadName) {
        this.dyadName = dyadName;
    }

    public String getSideA() {
        return sideA;
    }

    public void setSideA(String sideA) {
        this.sideA = sideA;
    }

    public String getSideB() {
        return sideB;
    }

    public void setSideB(String sideB) {
        this.sideB = sideB;
    }

    public String getSourceArticle() {
        return sourceArticle;
    }

    public void setSourceArticle(String sourceArticle) {
        this.sourceArticle = sourceArticle;
    }

    public String getSourceOffice() {
        return sourceOffice;
    }

    public void setSourceOffice(String sourceOffice) {
        this.sourceOffice = sourceOffice;
    }

    public String getSourceDate() {
        return sourceDate;
    }

    public void setSourceDate(String sourceDate) {
        this.sourceDate = sourceDate;
    }

    public String getSourceHeadline() {
        return sourceHeadline;
    }

    public void setSourceHeadline(String sourceHeadline) {
        this.sourceHeadline = sourceHeadline;
    }

    public String getSourceOriginal() {
        return sourceOriginal;
    }

    public void setSourceOriginal(String sourceOriginal) {
        this.sourceOriginal = sourceOriginal;
    }

    public String getWhereCoordinates() {
        return whereCoordinates;
    }

    public void setWhereCoordinates(String whereCoordinates) {
        this.whereCoordinates = whereCoordinates;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
