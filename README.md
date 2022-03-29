# gendercomics-api

API for accessing the gendercomics.net database

![build status master branch](https://github.com/gendercomics/api/actions/workflows/docker-image.yml/badge.svg?branch=master)

Documentation: https://api.gendercomics.net/swagger-ui.html

## Release Notes
---
### gendercomics-api-1.17.0
- TODO

---
### gendercomics-api-1.16.1
- JsonMapping-NullPointer FIX for Series.getComicId()

---
### gendercomics-api-1.16.0
- when deleting a comic of type *_series, then the entries in the related comics are deleted
- Comic.getSeriesAsMap() made null-safe and ignored in JSON serialization

---
### gendercomics-api-1.15.2
- hotfix for nullpointer exception on getComparableNameForWebAppList

---
### gendercomics-api-1.15.1
- hotfix for nullpointer exception on getComparableNameForWebAppList

---
### gendercomics-api-1.15.0
- regex search service fully implemented

---
### gendercomics-api-1.14.0
- removed deprecated person name attributes
- regex search service (without controller)

---
### gendercomics-api-1.13.0
- cover image upload
- cover image download from DNB
- cover image download for all comics having isbn13 and an image available at DNB
- cleanup endpoint for person name attributes

---
### gendercomics-api-1.12.3
- HOTFIX for HOTFIX for log4j vulnerability (log4j:2.16.0)

---
### gendercomics-api-1.12.2
- HOTFIX for log4j vulnerability (log4j:2.15.0)

---
### gendercomics-api-1.12.1
- HOTFIX for wrong mongodb start command

---
### gendercomics-api-1.12.0
- removed previous migration endpoints (series, links, publisher, roles)
- migration cleanup (removal) endpoint for publisher
- removed comic.series single attribute
- removed comic.hyperlink single attribute
- migration cleanup (removal) endpoint for comic.creator.role (single entry)
- list and cleanup of empty hyperlinks (MigrationController)
- transient location override for specific publisher
- save publisher location override in Hashmap (via service)

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

