import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IHistorique } from 'app/shared/model/historique.model';
import { getEntities } from './historique.reducer';

export const Historique = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const historiqueList = useAppSelector(state => state.historique.entities);
  const loading = useAppSelector(state => state.historique.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="historique-heading" data-cy="HistoriqueHeading">
        <Translate contentKey="drugStoreApp.historique.home.title">Historiques</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="drugStoreApp.historique.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/historique/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="drugStoreApp.historique.home.createLabel">Create new Historique</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {historiqueList && historiqueList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="drugStoreApp.historique.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.historique.action">Action</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.historique.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="drugStoreApp.historique.session">Session</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {historiqueList.map((historique, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/historique/${historique.id}`} color="link" size="sm">
                      {historique.id}
                    </Button>
                  </td>
                  <td>{historique.action}</td>
                  <td>{historique.date ? <TextFormat type="date" value={historique.date} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{historique.session ? <Link to={`/session/${historique.session.id}`}>{historique.session.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/historique/${historique.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/historique/${historique.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/historique/${historique.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="drugStoreApp.historique.home.notFound">No Historiques found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Historique;
