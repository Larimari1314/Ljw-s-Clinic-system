package sale.ljw.clinic_administrator_background_system.service.clinic;

public interface ReservationInformationService {
    public abstract String findByDid(String did);
    public abstract String findAll(Integer page);

    public abstract String deleteReservation(String id);

    public abstract String findById(String id);

    public abstract String findByName(String name,Integer page);
}
