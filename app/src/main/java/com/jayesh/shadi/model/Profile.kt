package com.jayesh.shadi.model

import java.io.Serializable

data class Results(
    var results:MutableList<Profile>,
): Serializable

data class Profile(
    var gender:String,
    var name:FullName,
    var location:Address,
    var email:String,
    var login:LoginDetail,
    var dob:DateAge,
    var registered:DateAge,
    var phone :String,
    var cell: String,
    var id:IDdetail,
    var picture:Pictures,
    var nt:String,

    )

data class FullName(
    var title:String,
    var first:String,
    var last:String,
)
data class Address(
    var street:Street,
    var city:String,
    var state:String,
    var country:String,
    var postcode:String,
    var coordinates:Coordinates,
    var timezone:Timezone,
)

data class Coordinates(
    var latitude:String,
    var longitude:String
)
data class Timezone(
    var offset:String,
    var description:String,
)
data class Street(
    var number:String,
    var name:String
)

data class LoginDetail(
    var uuid:String,
    var username:String,
    var password:String,
    var salt:String,
    var md5:String,
    var sha1:String,
    var sha256:String,
)

data class DateAge(
    var date:String,
    var age:String,
)
data class IDdetail(
    var name:String,
    var value:String,
)
data class Pictures(
    var large:String,
    var medium:String,
    var thumbnail:String,
)





