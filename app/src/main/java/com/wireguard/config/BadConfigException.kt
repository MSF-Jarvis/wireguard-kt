/*
 * Copyright © 2018 WireGuard LLC. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package com.wireguard.config

import androidx.annotation.Nullable
import com.wireguard.crypto.KeyFormatException
import com.wireguard.crypto.ParseException

class BadConfigException private constructor(
    val section: Section,
    val location: Location,
    val reason: Reason,
    @param:Nullable @field:Nullable @get:Nullable
        val text: CharSequence?,
    @Nullable cause: Throwable?
) : Exception(cause) {

    constructor(
        section: Section,
        location: Location,
        reason: Reason,
        @Nullable text: CharSequence
    ) : this(section, location, reason, text, null)

    constructor(
        section: Section,
        location: Location,
        cause: KeyFormatException
    ) : this(section, location, Reason.INVALID_KEY, null, cause)

    constructor(
        section: Section,
        location: Location,
        @Nullable text: CharSequence,
        cause: NumberFormatException
    ) : this(section, location, Reason.INVALID_NUMBER, text, cause)

    constructor(
        section: Section,
        location: Location,
        cause: ParseException
    ) : this(section, location, Reason.INVALID_VALUE, cause.text, cause)

    enum class Location private constructor(val name: String) {
        TOP_LEVEL(""),
        ADDRESS("Address"),
        ALLOWED_IPS("AllowedIPs"),
        DNS("DNS"),
        ENDPOINT("Endpoint"),
        EXCLUDED_APPLICATIONS("ExcludedApplications"),
        LISTEN_PORT("ListenPort"),
        MTU("MTU"),
        PERSISTENT_KEEPALIVE("PersistentKeepalive"),
        PRE_SHARED_KEY("PresharedKey"),
        PRIVATE_KEY("PrivateKey"),
        PUBLIC_KEY("PublicKey")
    }

    enum class Reason {
        INVALID_KEY,
        INVALID_NUMBER,
        INVALID_VALUE,
        MISSING_ATTRIBUTE,
        MISSING_SECTION,
        MISSING_VALUE,
        SYNTAX_ERROR,
        UNKNOWN_ATTRIBUTE,
        UNKNOWN_SECTION
    }

    enum class Section private constructor(val name: String) {
        CONFIG("Config"),
        INTERFACE("Interface"),
        PEER("Peer")
    }
}