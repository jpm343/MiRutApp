package com.example.mirutapp.Model;

// not an entity, just a transient class
public class VehicleRestriction {
    //these arguments should be replaced. We are using a temporary webservice.
    private int en_vacaciones;
    private String contaminante;
    private String fecha;
    private String fecha_title;
    private String tipo_alerta;

    // the following 4 fields are important.
    private String csv;
    private String ssv;
    private String truck_ssv;
    private String truck_csv;

    private String url;
    private int id;
    private String cars_date;
    private String bikes_date;

    //getters and setters
    public int getEn_vacaciones() { return en_vacaciones; }
    public String getContaminante() { return contaminante; }
    public String getFecha() { return fecha; }
    public String getFecha_title() { return fecha_title; }
    public String getTipo_alerta() { return tipo_alerta; }
    public String getCsv() { return csv; }
    public String getSsv() { return ssv; }
    public String getTruck_ssv() { return truck_ssv; }
    public String getTruck_csv() { return truck_csv; }
    public String getUrl() { return url; }
    public int getId() { return id; }
    public String getCars_date() { return cars_date; }
    public String getBikes_date() { return bikes_date; }

    public void setEn_vacaciones(int en_vacaciones) { this.en_vacaciones = en_vacaciones; }
    public void setContaminante(String contaminante) { this.contaminante = contaminante; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setFecha_title(String fecha_title) { this.fecha_title = fecha_title; }
    public void setTipo_alerta(String tipo_alerta) { this.tipo_alerta = tipo_alerta; }
    public void setCsv(String csv) { this.csv = csv; }
    public void setSsv(String ssv) { this.ssv = ssv; }
    public void setTruck_ssv(String truck_ssv) { this.truck_ssv = truck_ssv; }
    public void setTruck_csv(String truck_csv) { this.truck_csv = truck_csv; }
    public void setUrl(String url) { this.url = url; }
    public void setId(int id) { this.id = id; }
    public void setCars_date(String cars_date) { this.cars_date = cars_date; }
    public void setBikes_date(String bikes_date) { this.bikes_date = bikes_date; }
}
