package fr.sysf.sample.models


case class OauthCreateUser(
                            user_id: Option[String] = None,
                            email: Option[String] = None,
                            username: Option[String] = None,
                            password: Option[String] = None,
                            name: Option[String] = None,
                            nickname: Option[String] = None,
                            gender: Option[String] = None,
                            given_name: Option[String] = None,
                            family_name: Option[String] = None,
                            phone_number: Option[String] = None,
                            picture: Option[String] = None,
                            connection: Option[String] = Some("Username-Password-Authentication"),
                            email_verified: Option[Boolean] = Some(false),
                            verify_email: Option[Boolean] = Some(false),
                            phone_verified: Option[Boolean] = None,
                            user_metadata: Map[String, String] = Map.empty,
                            app_metadata: Map[String, String] = Map.empty
                          )

