package com.auction.andrew.auction.utility.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Already Created")
public class Forbidden extends RuntimeException {

}
