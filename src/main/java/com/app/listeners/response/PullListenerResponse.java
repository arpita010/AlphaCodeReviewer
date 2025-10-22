package com.app.listeners.response;

import com.app.constants.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PullListenerResponse {
  private ApiResponse status;
  private String message;
}
