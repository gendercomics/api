# gendercomics-api

API for accessing the gendercomics.net database


[![Build Status](https://travis-ci.com/gendercomics/api.svg?branch=develop)](https://travis-ci.com/gendercomics/api)
![GitHub issues](https://img.shields.io/github/issues/gendercomics/api.svg)

Documentation: https://api.gendercomics.net/swagger-ui.html

## Release Notes
---
### gendercomics-admin-webapp-1.6.2
- docker container configuration set to 'restart: always'
- comic_title_issue_index not unique

---
### gendercomics-admin-webapp-1.6.1
- series type removed

---
### gendercomics-admin-webapp-1.6.0
- ComicType.series replaced by comic_series and publishing_series
- added Comic.Printer [String]
- Comic.HyperLink replaces Comic.Link
- Comic.HyperLink includes last access date
- Comic.IssueTitle added
- integration test with TestContainers
- JUnit4 to JUnit5 upgrade

---
### gendercomics-admin-webapp-1.5.0
- Upgrade to Java 11

