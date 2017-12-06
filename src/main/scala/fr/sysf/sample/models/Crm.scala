package fr.sysf.sample.models

import java.time.LocalDate


case class CrmCreateCustomer(
                              email: Option[String],
                              firstName: Option[String],
                              surname: Option[String],
                              profilePicture: Option[String] = None,
                              gender: Option[String] = None,
                              dateOfBirth: Option[String] = None,
                              address1: Option[String] = None,
                              address2: Option[String] = None,
                              address3: Option[String] = None,
                              address4: Option[String] = None,
                              postCode: Option[String] = None,
                              city: Option[String] = None,
                              mobilePhone: Option[String] = None,
                              mobilePhonePrefix: Option[String] = None,
                              landlinePhone: Option[String] = None,
                              landlinePhonePrefix: Option[String] = None,
                              state: Option[String] = None,
                              countryCode: Option[String] = None,
                              languageCode: Option[String] = None,
                              latlong: Option[String] = None,
                              deviceToken: Option[String] = None,
                              devicePlatform: Option[String] = None,
                              duplicationStatus: Option[String] = None,
                              duplicationStatusSource: Option[String] = None,
                              duplicationStatusDate: Option[String] = None,
                              danoneBrandsOptins: OptIn = OptIn(),
                              danonePartnersOptins: OptIn = OptIn(),
                              doubleOptinConfirmed: Option[Boolean] = None,
                              doubleOptinToken: Option[String] = None,
                              portalAccounts: List[CrmPortalAccount] = List.empty,
                              targetClusterCodes: List[String] = List.empty,
                              sponsorshipCode: Option[String] = None,
                              sponsorCustomerId: Option[String] = None,
                              agreements: List[CrmAgreement] = List.empty
                            )

case class CrmCreateCustomerResponse(
                                      customerId: String
                                    )

case class CrmCustomer(
                        customerId: Option[String],
                        householdId: Option[String],
                        email: Option[String],
                        password: Option[String],
                        firstName: Option[String],
                        surname: Option[String],
                        fbId: Option[String],
                        profilePicture: Option[String],
                        gender: Option[String],
                        dateOfBirth: Option[LocalDate],
                        address1: Option[String],
                        address2: Option[String],
                        address3: Option[String],
                        address4: Option[String],
                        postCode: Option[String],
                        city: Option[String],
                        mobilePhone: Option[String],
                        mobilePhonePrefix: Option[String],
                        landlinePhone: Option[String],
                        landlinePhonePrefix: Option[String],
                        state: Option[String],
                        countryCode: Option[String],
                        languageCode: Option[String],
                        latlong: Option[String],
                        deviceToken: Option[String],
                        devicePlatform: Option[String],
                        duplicationStatus: Option[String],
                        duplicationStatusSource: Option[String],
                        duplicationStatusDate: Option[String],
                        danoneBrandsOptins: OptIn,
                        danonePartnersOptins: OptIn,
                        doubleOptinConfirmed: Option[Boolean],
                        doubleOptinToken: Option[String],
                        portalAccounts: List[CrmPortalAccount],
                        targetClusterCodes: List[Option[String]],
                        sponsorshipCode: Option[String],
                        sponsorCustomerId: Option[String],
                        numberOfChildren: Option[Int]
                      )

case class CrmPortalAccount(
                             portalBrandCode: Option[String],
                             status: Option[String],
                             registrationDate: Option[String],
                             programOptins: OptIn = OptIn(),
                             source: Option[String] = None,
                             medium: Option[String] = None,
                             campaign: Option[String] = None,
                             term: Option[String] = None,
                             content: Option[String] = None
                           )

case class OptIn(
                  sms: Boolean = false,
                  email: Boolean = false,
                  mail: Boolean = false
                )

case class CrmAgreement(
                         agreementName: Option[String],
                         agreementDate: Option[String],
                         agreementStatus: Option[String]
                       )
