package iamdhariot.github.retrofitphpapp.essentials

// extension functions
// for check email and/or password is valid or not
fun String.isEmailValid():Boolean = contains("@gmail.com") ||
        contains("@outlook.com") ||
        contains("@hotmail.com")
// Name should contain atleast 4 characters.
fun String.isNameValid(): Boolean {
    return length>= PASSWORD_LENGTH
}
// Password should contain atleast 6 characters.
fun String.isPasswordValid(): Boolean {
    return length>= PASSWORD_LENGTH
}
