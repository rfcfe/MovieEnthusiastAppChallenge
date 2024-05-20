import React from "react";
import "./movieModal.css";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { formatNumberWithCommas } from "../../utils/DataManipulation";

const MovieModal = ({ onClose, movie }) => {
  const handleClick = (event) => {
    event.stopPropagation();
  };
  return (
    <div className="modal-overlay">
      <div className="modal-content" onClick={handleClick}>
        <div className="modal-header">
          <h2 className="modal-title">{movie.title}</h2>
          <div className="modal-close-container" onClick={onClose}>
            <div className="modal-close-icon">
              <FontAwesomeIcon icon={faTimes} />
            </div>
            <div className="modal-close-text">CLOSE</div>
          </div>
        </div>
        <div className="line"></div>
        <div className="modal-information">
          <div className="modal-subtitle">Year</div>
          <div className="modal-text">{movie.release_date}</div>
          <div className="modal-subtitle">Genre</div>
          <div className="modal-text">{movie.genre.join(", ")}</div>
          <div className="modal-subtitle">Description</div>
          <div className="modal-text">{movie.overview}</div>
          <div className="crew-container">
            <div className="crew-group">
              <div className="modal-crew-subtitle">Director</div>
              <div className="modal-crew-text">{movie.director.join("  ")}</div>
            </div>
            <div className="crew-group">
              <div className="modal-crew-subtitle">Actors</div>
              <div className="modal-crew-text">
                {movie.actor.join("\u00A0\u00A0")}
              </div>
            </div>
          </div>

          <div className="modal-subtitle">Runtime</div>
          <div className="modal-text">{movie.runtime} mins </div>
          <div className="modal-subtitle">Rating</div>
          <div className="modal-text">{movie.vote_average}</div>
          <div className="modal-subtitle">Votes</div>
          <div className="modal-text">{movie.vote_count}</div>
          <div className="modal-subtitle">Revenue</div>
          <div className="modal-text">
            ${formatNumberWithCommas(movie.revenue)}
          </div>
        </div>
      </div>
    </div>
  );
};

export default MovieModal;
