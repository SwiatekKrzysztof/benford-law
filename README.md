# Benford data analyzer

## Notes
- Project is using **reactive programming**. It was chosen for scalability and optimization,
  when dealing with uploading and parsing text files we could easily control backpressure,
  and deliver output to user when we obtain it, switching threads as needed.
- For Benford law test I chose one based on John Morrow paper: http://www.johnmorrow.info/projects/benford/benfordMain.pdf
- Utilising **SSE (Server-Sent Events)** we avoid unnecessary polling from users
- We store `UUID`s of user's uploaded documents in browser `Local Storage`
  so user can come back, and not search through all documents uploaded
- Chart colors were picked with color-blindness in mind
- **FOR STRETCH CHALLENGE**: It would need clarification is all the information uploaded by user important here or only the relevant column? I assumed former. Based on that, it would be bad idea to store potentially large files (maximal size should probably be decided for production) inside database. Because of that, we store files in directory with name changed to `UUID`, and store only reference in database.

## Running project

```sh
cd benford
docker-compose up
```

## Tests
If using java 13+ tests need to be run with
```sh
-XX:+AllowRedefinitionToAddDeleteMethods
```
as required by Blockhound

## Test files
Integration tests are using Test Containers, so copy of schema.sql is needed in test resource folder\
\
Files for testing:
 - uscities2.csv taken from https://github.com/rleeap/Benfords-Law and modified as dataset conforming Benford Law
 - census_2009b.dms attached to task

## Further development

- To make it as scalable as possible we could use kafka/rabbitMQ as message broker
- Implement "Your graph is ready" mechanism on frontend
- Find a way to get rid of schema.sql in test/resources directory, as it is copy of benford/schema.sql (see **Tests** section)
- Implement some sort of gateway for requests, that could control number of calls to API
## Task description

In 1938, Frank Benford published a paper showing the distribution of the leading digit in many disparate sources of data. In all these sets of data, the number 1 was the leading digit about 30% of the time. Benford’s law has been found to apply to population numbers, death rates, lengths of rivers, mathematical distributions given by some power law, and physical constants like atomic weights and specific heats.

Create a python-based web application (use of tornado or flask is fine) that

1) can ingest the attached example file (census_2009b) and any other flat file with a viable target column. Note that other columns in user-submitted files may or may not be the same as the census data file and users are known for submitting files that don't always conform to rigid expectations. How you deal with files that don't conform to the expectations of the application is up to you, but should be reasonable and defensible.

2) validates Benford’s assertion based on the '7_2009' column in the supplied file

3) Outputs back to the user a graph of the observed distribution of numbers with an overlay of the expected distribution of numbers. The output should also inform the user of whether the observed data matches the expected data distribution.

The delivered package should contain a docker file that allows us to docker run the application and test the functionality directly.

Bonus points for automated tests.

Stretch challenge: persist the uploaded information to a database so a user can come to the application and browse through datasets uploaded by other users. No user authentication/user management is required here… assume anonymous users and public datasets.