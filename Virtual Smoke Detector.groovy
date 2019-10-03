/***********************************************************************************************************************
 *
 *  A Virtual Smoke Detector.
 *
 *  License:
 *  This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 *  General Public License as published by the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *  for more details.
 *
 *  Name: Virtual Smoke Detector
 *
 ***********************************************************************************************************************/

public static String version() { return "v0.1.0" }

metadata {
	definition(name: "Virtual Smoke Detector", namespace: "captncode", author: "captncode", component: true) {
		capability "SmokeDetector"
		command "clear"
		command "detected"
	}
	preferences {
		input name: "txtEnable", type: "bool", title: "Enable descriptionText logging", defaultValue: true
	}
}

def updated() {
	log.info "Updated..."
	log.warn "${device.label} description logging is: ${txtEnable == true}"
}

def installed() {
	"Installed..."
	device.updateSetting("txtEnable", [type: "bool", value: true])
	refresh()
}

def parse(String description) { log.warn "parse(String description) not implemented" }

def parse(List description) {
	description.each {
		if (it.name in ["smoke"]) {
			if (txtEnable) log.info it.descriptionText
			sendEvent(it)
		}
	}
	return
}

def clear() {
	def descriptionText = "smoke is clear"
	sendEvent(name: "smoke", value: "clear", descriptionText: descriptionText)
	if (txtEnable) log.info "${device.label} ${descriptionText}"
}

def tested() {
}

def detected() {
	def descriptionText = "smoke is detected"
	sendEvent(name: "smoke", value: "detected", descriptionText: descriptionText, isStateChange: true)
	if (txtEnable) log.info "${device.label} ${descriptionText}"
}