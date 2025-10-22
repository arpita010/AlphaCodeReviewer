package com.app.services;

import com.app.listeners.request.PullEditRequest;
import com.app.listeners.response.PullListenerResponse;

public interface PullListenerService {
  PullListenerResponse publishEvent(PullEditRequest request);
}
