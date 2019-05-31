package com.example.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class EmptyJsonResponse {

   @Override
   public String toString() {
      return "{}";
   }
}
