import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye } from "@fortawesome/free-solid-svg-icons";
import "./table.css";
import { formatNumberWithCommas } from "../../utils/DataManipulation";

function Table({ headers, movies, handleMovieClick}) {
  return (
    <div id= "table-container" className="table-container">
      <table>
        <thead>
          <tr>
            {headers.map((text, index) => (
              <th className={text} key={index}>{text}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {movies.map((movie, index) => (
            <tr key={index}>
              <td>{index + 1}</td>
              <td>{movie.title}</td>
              <td>{movie.release_date}</td>
              <td>
                {movie.revenue === 0
                  ? "Not Available"
                  : `$${formatNumberWithCommas(movie.revenue)}`}
              </td>
              <td>
                <div className="eye-icon">
                  <FontAwesomeIcon
                    icon={faEye}
                    onClick={() => handleMovieClick(movie)}
                  />
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div id="loader" className="loader"></div>
    </div>
  );
}

export default Table;
