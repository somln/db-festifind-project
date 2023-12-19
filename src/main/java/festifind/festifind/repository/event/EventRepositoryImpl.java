package festifind.festifind.repository.event;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import festifind.festifind.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Event> searchEventsByGenreAndRegion(Long selectedGenreId,
                                                    Long selectedRegionId) {
        QEvent event = QEvent.event;
        QGenre genre = QGenre.genre;
        QRegion region = QRegion.region;

        BooleanExpression whereClause = event.isNotNull();

        if (selectedGenreId != null) {
            whereClause = whereClause.and(event.genre.id.eq(selectedGenreId));
        }

        if (selectedRegionId != null) {
            whereClause = whereClause.and(event.region.id.eq(selectedRegionId));
        }

        return queryFactory
                .selectFrom(event)
                .leftJoin(event.genre, genre).fetchJoin()
                .leftJoin(event.region, region).fetchJoin()
                .where(whereClause)
                .fetch();
    }

    @Override
    public List<Event> searchEventsByKeyword(String keyword) {
        QEvent event = QEvent.event;

        BooleanExpression whereClause = event.isNotNull();

        if (StringUtils.hasText(keyword)) {
            whereClause = whereClause.and(
                    event.title.containsIgnoreCase(keyword)
                            .or(event.place.containsIgnoreCase(keyword))
            );
        }

        return queryFactory
                .selectFrom(event)
                .where(whereClause)
                .fetch();
    }
}


