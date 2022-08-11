package example.com.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "conflictEntity")
public class Conflict {
    @PrimaryKey
    int id;
    @SerializedName("country_id")
    int countryId;
    String region;
    @SerializedName("date_start")
    String dateStart;
    @SerializedName("date_end")
    String dateEnd;
    @SerializedName("deaths_a")
    int deathsA;
    @SerializedName("deaths_b")
    int deathsB;
    @SerializedName("deaths_civilians")
    int deathsCivilians;
    @SerializedName("deaths_unknown")
    int deathsUnknown;
    int year;
    @SerializedName("conflict_new_id")
    int conflictNewId;
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
    @SerializedName("adm_1")
    String adm1;
    @SerializedName("adm_2")
    String adm2;
    double latitude;
    double longitude;
    String country;
    boolean isLoading;

    public Conflict() {}

    public Conflict(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public Conflict(int id, String conflict_name, double latitude, double longitude, String country) {
        this.id = id;
        this.conflictName = conflict_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
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

    public int getDeathsA() {
        return deathsA;
    }

    public void setDeathsA(int deathsA) {
        this.deathsA = deathsA;
    }

    public int getDeathsB() {
        return deathsB;
    }

    public void setDeathsB(int deathsB) {
        this.deathsB = deathsB;
    }

    public int getDeathsCivilians() {
        return deathsCivilians;
    }

    public void setDeathsCivilians(int deathsCivilians) {
        this.deathsCivilians = deathsCivilians;
    }

    public int getDeathsUnknown() {
        return deathsUnknown;
    }

    public void setDeathsUnknown(int deathsUnknown) {
        this.deathsUnknown = deathsUnknown;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getConflictNewId() {
        return conflictNewId;
    }

    public void setConflictNewId(int conflictNewId) {
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

    public String getAdm1() {
        return adm1;
    }

    public void setAdm1(String adm1) {
        this.adm1 = adm1;
    }

    public String getAdm2() {
        return adm2;
    }

    public void setAdm2(String adm2) {
        this.adm2 = adm2;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
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
