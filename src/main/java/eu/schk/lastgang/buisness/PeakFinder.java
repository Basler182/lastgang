package eu.schk.lastgang.buisness;

import eu.schk.lastgang.model.Peak;
import eu.schk.lastgang.model.LoadProfile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public final class PeakFinder {

    private PeakFinder(){
        throw new IllegalStateException("Utility class");
    }


    public static List<Peak> findPeaks(List<LoadProfile> loadProfiles, Double peakThreshold) {

        List<Peak> peaks = new ArrayList<>();

        LoadProfile start = null;
        LoadProfile end = null;

        for (LoadProfile loadProfile : loadProfiles) {

            if (loadProfile.value() > peakThreshold && start == null) {
                start = loadProfile;
            }

            if (loadProfile.value() < peakThreshold && start != null) {
                end = loadProfile;
            }

            if (start != null && end != null) {
                peaks.add(new Peak(start.dateTime(),
                        end.dateTime(),
                        null,
                        ChronoUnit.MINUTES.between(start.dateTime(), end.dateTime())));
                start = null;
                end = null;
            }
        }

        List<Peak> peaksWithValues = new ArrayList<>();
        for (Peak peak : peaks) {
            LocalDateTime start1 = peak.getStart();
            LocalDateTime end1 = peak.getEnd();
            var sum = loadProfiles.stream().filter(lg -> lg.dateTime().isAfter(start1) && lg.dateTime().isBefore(end1)).toList();

            OptionalDouble average = sum.stream().mapToDouble(LoadProfile::value).average();

            if (average.isPresent()) {

                var avg = (average.getAsDouble() - peakThreshold) / 4 * sum.size();
                BigDecimal bd = new BigDecimal(avg).setScale(2, RoundingMode.HALF_UP);

                peaksWithValues.add(new Peak(peak.getStart(), peak.getEnd(), bd.doubleValue(), peak.getDuration()));
            }
        }
        return peaksWithValues;
    }
}

