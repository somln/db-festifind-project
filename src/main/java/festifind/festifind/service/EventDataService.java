package festifind.festifind.service;

import festifind.festifind.entity.Event;
import festifind.festifind.repository.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EventDataService {


    private final EventRepository eventRepository;

    public Event generateRandomEvent() {

        List<Event> allEvents = eventRepository.findAll();
        if (allEvents.isEmpty()) {
            throw new RuntimeException("No users available in the database");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(allEvents.size());
        return allEvents.get(randomIndex);
    }
}
