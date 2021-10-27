# gendercomics-api

API for accessing the gendercomics.net database

![build status master branch](https://github.com/gendercomics/api/actions/workflows/docker-image.yml/badge.svg?branch=master)

Documentation: https://api.gendercomics.net/swagger-ui.html

## Release Notes
---
### gendercomics-api-1.11.0
- multiple series in comic (+ migration endpoint)

---
### gendercomics-api-1.10.1
- comic list sorted alphabetically again

---
### gendercomics-api-1.10.0
- mutliple hyperlinks (+ migration and delete endpoint)

---
### gendercomics-api-1.9.0
- multiple publishers fixes

---
### gendercomics-api-1.8.0
- multiple publishers (+ migration endpoint)
- multiple roles for a creator (+ migration endpoint)

---
### gendercomics-api-1.7.0
- CI/CD using github actions

---
### gendercomics-api-1.6.2
- docker container configuration set to 'restart: always'
- comic_title_issue_index not unique

---
### gendercomics-api-1.6.1
- series type removed

---
### gendercomics-api-1.6.0
- ComicType.series replaced by comic_series and publishing_series
- added Comic.Printer [String]
- Comic.HyperLink replaces Comic.Link
- Comic.HyperLink includes last access date
- Comic.IssueTitle added
- integration test with TestContainers
- JUnit4 to JUnit5 upgrade

---
### gendercomics-api-1.5.0
- Upgrade to Java 11

