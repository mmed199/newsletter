package com.mbds.newsletter.models

import java.io.Serializable

class SourcesResponse(val status: String, val sources: Array<Source>) :Serializable {

}