package com.soywiz.korim.font

import com.soywiz.korio.compression.deflate.ZLib
import com.soywiz.korio.compression.uncompress
import com.soywiz.korio.util.encoding.fromBase64
import kotlin.native.concurrent.ThreadLocal

// Sani Trixie Sans
// 1.6.1 - December 3, 2014
// © 2009 - 2014 Grandoplex Productions.
// Grandoplex Productions
// GrandChaos9000
// This font was licensed from SIL Open Font Library 1.1.
// 335 characters
private val DefaultTtfFontBase64 ="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"

@ThreadLocal
val DefaultTtfFont by lazy { TtfFont(DefaultTtfFontBase64.fromBase64().uncompress(ZLib)) }
