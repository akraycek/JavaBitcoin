/**
 * Copyright 2014 Ronald W Hoffman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ScripterRon.JavaBitcoin;

/**
 * This exception is thrown when an error is detected while processing an RPC request
 */
public class RequestException extends Exception {

    /** The RPC error code */
    private final int errorCode;

    /**
     * Creates a new exception
     *
     * @param       errorCode       Error code
     * @param       errorMessage    Error message
     */
    public RequestException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    /**
     * Returns the error code for this exception
     *
     * @return                      Error code
     */
    public int getCode() {
        return errorCode;
    }
}
