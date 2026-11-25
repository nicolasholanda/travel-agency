package dev.ia.travel;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BookingTools {

    @Inject
    BookingService bookingService;

    @Tool(description = "Obtém os detalhes comppletos de uma reserva com base em seu número de identificação (bookingId).")
    public String getBookingDetails(@ToolArg long bookingId) {
        return bookingService.getBookingDetails(bookingId)
                .map(Booking::toString)
                .orElse("Reserva com id " + bookingId + " não encontrada.");
    }

    @Tool(description = """
    Cancela uma reserva existente com base no seu ID (bookingId). O usuário deve estar autenticado.
    """)
    public String cancelBooking(@ToolArg long bookingId, @ToolArg String name) {
        return bookingService.cancelBooking(bookingId, name)
                .map(booking -> "Reserva" + bookingId + " cancelada com sucesso. Status atual: " + booking.status())
                .orElse("Não foi possível cancelar a reserva com id " + bookingId + ". Verifique se o id está correto e se você tem permissão.");
    }

    @Tool(description = "Lista todos os pacotes disponíveis em uma categoria específica (ex: ADVENTURE, TREASURES).")
    public String listPackagesByCategory(@ToolArg Category category) {
        var packages = bookingService.findPackagesByCategory(category);
        if (packages.isEmpty()) {
            return "Nenhum pacote encontrado para a categoria: " + category;
        }
        return "Pacotes encontrados na categoria " + category + ": " + packages.stream().map(Booking::destination).toList();
    }
}
