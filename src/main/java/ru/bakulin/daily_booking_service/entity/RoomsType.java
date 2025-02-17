package ru.bakulin.daily_booking_service.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoomsType {
  STUDIO("Студия"),
  ONE_ROOM("Одна комната"),
  TWO_ROOM("Две комнаты"),
  THREE_ROOM("Три комнаты"),
  FOUR_OR_MORE_ROOM("Четыре и более комнат");
  private final String description;
}
