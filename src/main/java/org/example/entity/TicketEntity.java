package org.example.entity;


import java.time.Duration;

public abstract class TicketEntity {

    /**
     * The city of departure.
     */
    private String origin;

    /**
     * The city of arrival.
     */
    private String destination;

//    @JsonProperty("departureTime")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//    private LocalDateTime departureTime;
//
//    @JsonProperty("arrivalTime")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//    private LocalDateTime arrivalTime;

    private String carrier;
    private int price;

//    @JsonProperty("segments")
//    private List<FlightSegmentEntity> segments;


    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

//    public LocalDateTime getDepartureTime() {
//        return departureTime;
//    }
//
//    public void setDepartureTime(LocalDateTime departureTime) {
//        this.departureTime = departureTime;
//    }
//
//    public LocalDateTime getArrivalTime() {
//        return arrivalTime;
//    }
//
//    public void setArrivalTime(LocalDateTime arrivalTime) {
//        this.arrivalTime = arrivalTime;
//    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public abstract Duration getTotalFlightDuration();
//    public List<FlightSegmentEntity> getSegments() {
//        return segments;
//    }
//
//    public void setSegments(List<FlightSegmentEntity> segments) {
//        this.segments = segments;
//    }
//
//    public Duration getTotalFlightDuration() {
//        return segments.stream()
//                .map(segment -> Duration.between(segment.getDepartureTime(), segment.getArrivalTime()))
//                .reduce(Duration.ZERO, Duration::plus);
//    }

}