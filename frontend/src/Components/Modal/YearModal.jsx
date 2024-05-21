import React from "react";
import "./yearModal.css";

const YearModal = ({ onSelect, onClose }) => {
  const yearsArray = Array.from(
    { length: new Date().getFullYear() - 1950 + 1 },
    (_, index) => 1950 + index
  ).reverse();
  return (
    <>
      <div className="year-modal-overlay" onClick={onClose}></div>
      <div className="a">
        <div
          className="year-modal-content"
          onClick={(e) => e.stopPropagation()}
        >
          <h2 className="year-modal-title">Select a Year</h2>
          <ul className="year-list">
            {yearsArray.map((year) => (
              <li
                key={year}
                onClick={() => onSelect(year)}
                className="year-item"
              >
                {year}
              </li>
            ))}
          </ul>
        </div>
      </div>
    </>
  );
};

export default YearModal;
