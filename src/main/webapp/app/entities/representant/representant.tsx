import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRepresentant } from 'app/shared/model/representant.model';
import { getEntities } from './representant.reducer';

export const Representant = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const representantList = useAppSelector(state => state.representant.entities);
  const loading = useAppSelector(state => state.representant.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="representant-heading" data-cy="RepresentantHeading">
        <Translate contentKey="drugStoreApp.representant.home.title">Representants</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="drugStoreApp.representant.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/representant/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="drugStoreApp.representant.home.createLabel">Create new Representant</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {representantList && representantList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="drugStoreApp.representant.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.representant.entite">Entite</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.representant.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.representant.prenom">Prenom</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.representant.telephone">Telephone</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.representant.cIN">C IN</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {representantList.map((representant, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/representant/${representant.id}`} color="link" size="sm">
                      {representant.id}
                    </Button>
                  </td>
                  <td>{representant.entite}</td>
                  <td>{representant.nom}</td>
                  <td>{representant.prenom}</td>
                  <td>{representant.telephone}</td>
                  <td>{representant.cIN}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/representant/${representant.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/representant/${representant.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/representant/${representant.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="drugStoreApp.representant.home.notFound">No Representants found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Representant;
