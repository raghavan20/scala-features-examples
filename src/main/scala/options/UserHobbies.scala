package options

object UserHobbies extends App {

    getHobbiesForUser("rag")
    getHobbiesForUser("john")
    getHobbiesForUser("mark")

    def getHobbiesForUser(name: String) {
        getUser(name) match {
            case Some(u) => getHobbies(u.id) match {
                case Some(hobbies) => println(s"$name hobbies - " + hobbies.mkString(","))
                case None => println(s"$name does not have hobbies")
            }
            case None => println(s"no user found for name: $name")
        }
    }


    def getHobbies(id: Int): Option[List[Hobby]] = {
        if (id == 1) Some(List(Hobby("badminton"), Hobby("fifa")))
        else None
    }

    def getUser(name: String): Option[User] = {
        if (name == "rag") Some(User(1, "rag"))
        else if (name == "john") Some(User(2, "john"))
        else None
    }

    case class User(id: Int, name: String)

    case class Hobby(id: String)

}
