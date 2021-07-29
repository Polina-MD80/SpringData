package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TicketDto;
import softuni.exam.models.dto.TicketRootDto;
import softuni.exam.models.entity.Ticket;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TicketServiceImpl implements TicketService {
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final TicketRepository ticketRepository;
    private final TownRepository townRepository;
    private final PassengerRepository passengerRepository;
    private final PlaneRepository planeRepository;

    public TicketServiceImpl(XmlParser xmlParser, ValidationUtil validationUtil,
                             ModelMapper modelMapper, TicketRepository ticketRepository,
                             TownRepository townRepository, PassengerRepository passengerRepository,
                             PlaneRepository planeRepository) {
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.ticketRepository = ticketRepository;
        this.townRepository = townRepository;
        this.passengerRepository = passengerRepository;
        this.planeRepository = planeRepository;
    }

    @Override
    public boolean areImported() {
        return ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xml/tickets.xml"));
    }

    @Override
    public String importTickets() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        TicketRootDto ticketRootDto = xmlParser.fromFile("src/main/resources/files/xml/tickets.xml", TicketRootDto.class);
        ticketRootDto.getTickets()
                .stream()
                .filter(ticketDto -> {
                    boolean isValid = validationUtil.isValid(ticketDto)
                            && townRepository.existsByName(ticketDto.getFromTown().getName())
                            && townRepository.existsByName(ticketDto.getToTown().getName())
                            && planeRepository.existsByRegisterNumber(ticketDto.getPlane().getRegisterNumber())
                            && passengerRepository.existsByEmail(ticketDto.getPassenger().getEmail());
                    sb.append(isValid ? String.format("Successfully imported Ticket %s - %s",
                            ticketDto.getFromTown().getName(), ticketDto.getToTown().getName())
                            : "Invalid Ticket")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(ticketDto -> {
                    Ticket ticket = modelMapper.map(ticketDto, Ticket.class);
                    setTicketFields(ticket, ticketDto);
                    return ticket;
                })
                .forEach(ticketRepository::save);

        return sb.toString();
    }

    private void setTicketFields(Ticket ticket, TicketDto ticketDto) {
        ticket.setFromTown(townRepository.getTownByName(ticketDto.getFromTown().getName()));
        ticket.setToTown(townRepository.getTownByName(ticketDto.getToTown().getName()));
        ticket.setPassenger(passengerRepository.findByEmail(ticketDto.getPassenger().getEmail()));
        ticket.setPlane(planeRepository.findByRegisterNumber(ticketDto.getPlane().getRegisterNumber()));
    }
}
