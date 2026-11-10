package dev.ai;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class BookingService {

    private final Map<Long, Booking> bookings = new HashMap<>();

    public BookingService() {
        bookings.put(12345L, new Booking(12345L, "John Doe", "Tesouros do Egito",
                LocalDate.now().plusMonths(2),
                LocalDate.now().plusMonths(2).plusDays(8), BookingStatus.CANCELLED));

        bookings.put(67890L, new Booking(67890L, "Jane Smith", "Aventura Amazônia",
                LocalDate.now().plusMonths(2),
                LocalDate.now().plusMonths(2).plusDays(8), BookingStatus.CANCELLED));
    }

    public Optional<Booking> getBookingDetails(Long id) {
        return Optional.ofNullable(bookings.get(id));
    }

    public Optional<Booking> cancelBooking(Long id, String customerLastName) {
        if (bookings.containsKey(id)) {
            Booking booking = bookings.get(id);
            if (booking.customerName().endsWith(customerLastName)) {
                Booking cancelledBooking = new Booking(
                        booking.id(),
                        booking.customerName(),
                        booking.destination(),
                        booking.startDate(),
                        booking.endDate(),
                        BookingStatus.CANCELLED
                );
                bookings.put(id, cancelledBooking);
                return Optional.of(cancelledBooking);
            }
        }
        return Optional.empty();
    }
}
