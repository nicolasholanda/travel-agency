package dev.ai;

import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BookingTools {

    @Inject
    BookingService bookingService;

    @Tool("Obtém os detalhes comppletos de uma reserva com base em seu número de identificação (bookingId).")
    public String getBookingDetails(long bookingId) {
        return bookingService.getBookingDetails(bookingId)
                .map(Booking::toString)
                .orElse("Reserva com id " + bookingId + " não encontrada.");
    }

    @Tool("""
    Cancela uma reserva existente com base no seu ID (bookingId). O usuário deve estar autenticado.
    """)
    public String cancelBooking(long bookingId) {
        return bookingService.cancelBooking(bookingId)
                .map(booking -> "Reserva" + bookingId + " cancelada com sucesso. Status atual: " + booking.status())
                .orElse("Não foi possível cancelar a reserva com id " + bookingId + ". Verifique se o id está correto e se você tem permissão.");
    }

    @Tool("Lista todos os pacotes disponíveis em uma categoria específica (ex: ADVENTURE, TREASURES).")
    public String listPackagesByCategory(Category category) {
        var packages = bookingService.findPackagesByCategory(category);
        if (packages.isEmpty()) {
            return "Nenhum pacote encontrado para a categoria: " + category;
        }
        return "Pacotes encontrados na categoria " + category + ": " + packages.stream().map(Booking::destination).toList();
    }
}
