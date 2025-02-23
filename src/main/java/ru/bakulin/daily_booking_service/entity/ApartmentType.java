package ru.bakulin.daily_booking_service.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApartmentType {
  ONLY_ROOM,
  ONE_ROOM,
  TWO_ROOM,
  THREE_ROOM,
  FOUR_OR_MORE_ROOM;
}
