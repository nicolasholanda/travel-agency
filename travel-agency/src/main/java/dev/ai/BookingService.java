package dev.ai;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class BookingService {

    private final Map<Long, Booking> bookings = new HashMap<>();

    public BookingService() {
        bookings.put(12345L, new Booking(12345L, "John Doe", "Tesouros do Egito",
                LocalDate.now().plusMonths(2),
                LocalDate.now().plusMonths(2).plusDays(8), BookingStatus.CANCELLED, Category.TREASURES));

        bookings.put(67890L, new Booking(67890L, "Jane Smith", "Aventura Amazônia",
                LocalDate.now().plusMonths(2),
                LocalDate.now().plusMonths(2).plusDays(8), BookingStatus.CANCELLED, Category.ADVENTURE));

        bookings.put(98765L, new Booking(98765L, "Peter Jones", "Trilha Inca",
                LocalDate.now().plusMonths(2),
                LocalDate.now().plusMonths(2).plusDays(8), BookingStatus.CANCELLED, Category.ADVENTURE));
    }

    public Optional<Booking> getBookingDetails(Long id) {
        return Optional.ofNullable(bookings.get(id));
    }

    public List<Booking> findPackagesByCategory(Category category) {
        return bookings.values().stream()
                .filter(booking -> booking.category() == category)
                .toList();
    }

    public Optional<Booking> cancelBooking(Long id) {
        String currentUser = SecurityContext.getCurrentUser();
        if (bookings.containsKey(id)) {
            Booking booking = bookings.get(id);
            if (booking.customerName().equals(currentUser)) {
                Booking cancelledBooking = new Booking(
                        booking.id(),
                        booking.customerName(),
                        booking.destination(),
                        booking.startDate(),
                        booking.endDate(),
                        BookingStatus.CANCELLED,
                        booking.category()
                );
                bookings.replace(id, cancelledBooking);
                return Optional.of(cancelledBooking);
            }
        }
        return Optional.empty();
    }
}
