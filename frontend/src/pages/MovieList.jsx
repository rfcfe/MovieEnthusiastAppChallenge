import React, { useState, useEffect, useRef, useCallback } from "react";
import "./MovieList.css";
import MovieModal from "../Components/Modal/MovieModal";
import Table from "../Components/Table/Table";
import {
  fetchMovies,
  fetchMovieDetails,
  fetchTopRevenueMovies,
  fetchTopRevenueMoviesPerYear,
} from "../services/DataFetch";
import Navbar from "../Components/Navbar/Navbar";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faRotateLeft } from "@fortawesome/free-solid-svg-icons";
import YearModal from "../Components/Modal/YearModal";

const MovieList = () => {
  const [movies, setMovies] = useState([]);
  const [page, setPage] = useState(1);
  const [showModal, setShowModal] = useState(false);
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [activeTop10, setActiveTop10] = useState(false);
  const [selectedYear, setSelectedYear] = useState("per Year");
  const [showYearModal, setShowYearModal] = useState(false);
  const [selectedYearTop10, setSelectedYearTop10] = useState(false);
  const [loading, setLoading] = useState(false);
  const observer = useRef(null);

  const handleMovieClick = async (movie) => {
    setSelectedMovie(movie);
    try {
      const data = await fetchMovieDetails(movie.id);
      setSelectedMovie(data);
      setShowModal(true);
    } catch (error) {
      console.error("Error fetching movie details:", error);
    }
  };

  const closeModal = () => {
    setShowModal(false);
    setShowYearModal(false);
  };

  const handleTop10Click = async () => {
    setActiveTop10(true);
    setSelectedYearTop10(false);
    try {
      const data = await fetchTopRevenueMovies();
      setMovies(data);
    } catch (error) {
      console.error("Error fetching top revenue movies:", error);
    }
  };

  const handleSelectYearClick = async () => {
    setShowYearModal(!showYearModal);
  };

  const selectYear = async (year) => {
    setShowYearModal(false);
    setSelectedYearTop10(true);
    setActiveTop10(false);
    try {
      const data = await fetchTopRevenueMoviesPerYear(year);
      setMovies(data);
      setSelectedYear(year);
    } catch (error) {
      console.error("Error fetching top revenue movies per year:", error);
    }
  };

  const resetFilter = () => {
    setSelectedYearTop10(false);
    setActiveTop10(false);
    setSelectedYear("per Year");
    setPage(1);
    setMovies([]);
  };

  useEffect(() => {
    const loadMovies = async () => {
      if (loading || activeTop10 || selectedYearTop10) return;
      setLoading(true);
      try {
        const data = await fetchMovies(page);
        setMovies((prevMovies) => [...prevMovies, ...data]);
      } catch (error) {
        console.error("Error fetching movies:", error);
      } finally {
        setLoading(false);
      }
    };
    loadMovies();
  }, [page, activeTop10, selectedYearTop10]);

  const prefetchNextPage = useCallback(() => {
    if (!loading && !activeTop10 && !selectedYearTop10) {
      setPage((prevPage) => prevPage + 1);
    }
  }, [loading, activeTop10, selectedYearTop10, loading]);

  useEffect(() => {
    if (observer.current) {
      observer.current.disconnect();
    }

    const handleIntersection = (entries) => {
      const target = entries[0];
      if (target.isIntersecting) {
        prefetchNextPage();
      }
    };
    const table = document.querySelector("#table-container");
    observer.current = new IntersectionObserver(handleIntersection, {
      root: table,
      rootMargin: "200px",
      threshold: 0.25,
    });

    const loader = document.querySelector("#loader");
    if (loader) {
      observer.current.observe(loader);
    }
    return () => {
      if (observer.current) observer.current.disconnect();
    };
  }, [prefetchNextPage]);

  return (
    <>
      <Navbar />
      <div className="page-content">
        <h1 className="page-title">Movie ranking</h1>
        <div className="filter-modal-container">
          <div className="filter-container">
            <button
              className={`filter-button ${activeTop10 ? "active" : ""}`}
              onClick={handleTop10Click}
            >
              Top 10 Revenue
            </button>
            <button
              className={`filter-button ${
                selectedYearTop10 || showYearModal ? "active" : ""
              }`}
              onClick={handleSelectYearClick}
            >
              Top 10 Revenue {selectedYear}
            </button>

            {(activeTop10 || selectedYearTop10) && (
              <div className="filter-reset" onClick={resetFilter}>
                <FontAwesomeIcon icon={faRotateLeft} />
              </div>
            )}
          </div>
          <div className="year-modal">
            {showYearModal && (
              <YearModal onSelect={selectYear} onClose={closeModal} />
            )}
          </div>
        </div>
        <Table
          headers={["RANKING", "TITLE", "YEAR", "REVENUE", "  "]}
          movies={movies}
          handleMovieClick={handleMovieClick}
        />
        {showModal && <MovieModal onClose={closeModal} movie={selectedMovie} />}
      </div>
    </>
  );
};

export default MovieList;
