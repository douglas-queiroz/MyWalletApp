package com.douglasqueiroz.mywallet.extensions

import kotlinx.datetime.Instant


fun Instant.toOnlyDate() = this.toString().substring(0, 10)