/*
 * Copyright (C) 2014 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.distantfuture.castcompanionlibrary.lib.cast.player;

import com.google.android.gms.cast.MediaInfo;

/**
 * A public interface that provides callbacks for the {@link IMediaAuthService} to communicate with
 * the framework
 */
public interface IMediaAuthListener {

  /**
   * Called when IMediaAuthService has successfully obtained a result.
   *
   * @param status  Provides the status of result, will be one of
   *                MediaAusthStatus#RESULT_AUTHORIZED or
   *                MediaAusthStatus#RESULT_NOT_AUTHORIZED
   * @param info    The fully populated {@link MediaInfo} that is obtained through authorization.
   * @param message If authorization was not granted, then an optional message can be provided to
   *                be presented to the user. If no message is provided, it will be silently ignored.
   *                Implementors have to make sure the message is localized.
   */
  public void onResult(MediaAuthStatus status, MediaInfo info, String message);

  /**
   * Called when IMediaAuthService returns with a failure message due to some issues such as
   * network, backend issues, etc.
   * <p/>
   * param failureMessage The message stating the reason for failure. This message should be
   * localized.
   */
  public void onFailure(String failureMessage);

}
